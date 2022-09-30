package com.example.absensikaryawan;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absensikaryawan.api.ApiClient;
import com.example.absensikaryawan.api.ApiInterface;
import com.example.absensikaryawan.model.absen.Absen;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;


public class AbsenActivity extends AppCompatActivity {
    TextView etAbsenName, etAbsenNip, etAbsenWaktu, etAbsenTanggal, etAbsenNIC;
    EditText etAbsenKeterangan;
    Button btnAbsen;
    String name, nip, keterangan, waktu, tanggal, alamat_ip;
    ProgressBar loading;
    int jam;

    ApiInterface apiInterface;
    SessionManager sessionManager;

    LinearLayout rootLayout, animasiLayout;
    AnimationDrawable animationDrawable;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen);

        etAbsenName = findViewById(R.id.etAbsenName);
        etAbsenNip = findViewById(R.id.etAbsenNip);
        etAbsenKeterangan = findViewById(R.id.etAbsenKeterangan);
        etAbsenWaktu = findViewById(R.id.etAbsenWaktu);
        etAbsenTanggal = findViewById(R.id.etAbsenTanggal);
        etAbsenNIC = findViewById(R.id.etAbsenNIC);

        loading = findViewById(R.id.loading);

        btnAbsen = findViewById(R.id.btnAbsen);

        rootLayout = findViewById(R.id.rootLayout);
        animasiLayout = findViewById(R.id.animasiLayout);

        //Perpindahan durasi setiap gambar
        animationDrawable = (AnimationDrawable) rootLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        //Memberikan Efek animasi pada Layout
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        animasiLayout.setAnimation(animation);

        //Membuat Waktu dan Tanggal
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat Waktu = new SimpleDateFormat(":mm:ss");
        SimpleDateFormat Tanggal = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        jam = calendar.get(Calendar.HOUR_OF_DAY);
        String finalTanggal = Tanggal.format(calendar.getTime());
        String finalWaktu = jam+""+Waktu.format(calendar.getTime());

        //Mencek Alamat IP Addreass
        List<String> daftarInterface = new ArrayList<String>();
        daftarInterface = getLocalIpv4Address();

        String nic="";
        for(int i=0; i<daftarInterface.size();i++)
            nic+=daftarInterface.get(i).toString()+" || ";
        etAbsenNIC.setText(nic);

        sessionManager = new SessionManager(AbsenActivity.this);
        name = sessionManager.getUserDetail().get(SessionManager.NAME);
        nip = sessionManager.getUserDetail().get(SessionManager.NIP);
        keterangan = sessionManager.getUserDetail().get(SessionManager.KETERANGAN);
        waktu = sessionManager.getUserDetail().get(SessionManager.WAKTU);
        tanggal = sessionManager.getUserDetail().get(SessionManager.TANGGAL);
        alamat_ip = sessionManager.getUserDetail().get(SessionManager.ALAMAT_IP);

        etAbsenName.setText(name);
        etAbsenNip.setText(nip);
        etAbsenWaktu.setText(finalWaktu);
        etAbsenTanggal.setText(finalTanggal);
        keterangan = etAbsenKeterangan.getText().toString();

        btnAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                btnAbsen.setVisibility(View.GONE);
                name = etAbsenName.getText().toString();
                nip = etAbsenNip.getText().toString();
                keterangan = etAbsenKeterangan.getText().toString();
                waktu = etAbsenWaktu.getText().toString();
                tanggal = etAbsenTanggal.getText().toString();
                alamat_ip = etAbsenNIC.getText().toString();
                if(keterangan.isEmpty()){
                    etAbsenKeterangan.setError("Mohon diisi terlebih dahulu");
                    loading.setVisibility(View.GONE);
                    btnAbsen.setVisibility(View.VISIBLE);
                }
                else {
                    absen(name, nip, keterangan, waktu, tanggal, alamat_ip);
                }
            }
        });

    }

    private void absen(String name, String nip, String keterangan, String waktu, String tanggal, String alamat_ip) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Absen>
                callAbsen = apiInterface.absenResponse(nip, name, keterangan, waktu, tanggal, alamat_ip);
        callAbsen.enqueue(new Callback<Absen>() {
            @Override
            public void onResponse(Call<Absen> callAbsen, Response<Absen> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(AbsenActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btnAbsen.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(AbsenActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btnAbsen.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Absen> callAbsen, Throwable t) {
                Toast.makeText(AbsenActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                btnAbsen.setVisibility(View.VISIBLE);
            }
        });
    }

    public List<String> getLocalIpv4Address(){

        String nicName, nicIpAddr, nicMacAddr="";
        List<String> daftarInterface = new ArrayList<String>();
        try{

            //cari tahu,daftar NIC yang terdapat di dalam device Android
            for(Enumeration<NetworkInterface> nicEnum =

                NetworkInterface.getNetworkInterfaces();
                nicEnum.hasMoreElements();){

                NetworkInterface nic = nicEnum.nextElement();
                //cari tahu, alamat IP yang terdapat di dalam setiap NIC tersebut

                for(Enumeration<InetAddress> ipEnum =

                    nic.getInetAddresses();
                    ipEnum.hasMoreElements();){
                    InetAddress ip = (InetAddress)ipEnum.nextElement();

                    //check, apakah NIC'nya memiliki alamat IPv4

                    //mendapatkan nama NIC
                    nicName = nic.getDisplayName();
                    //mendapatkan MAC addr NIC
                    byte[] mac = nic.getHardwareAddress();
                    if(mac!=null)
                        for(int i=0;i<mac.length;i++)
                            nicMacAddr +=
                                    String.format("%02X%s",
                                            mac[i],(i<mac.length-1)?"-":"");
                    //mendapatkan alamat IP pada NIC
                    nicIpAddr = ip.getHostAddress();
                    //tambahkan ke list
                    daftarInterface.add(nicName+" ; "+nicMacAddr+" "+nicIpAddr);

                }
            }
            return daftarInterface;
        }catch(SocketException se){
            Log.e("getLocalIpv4Address", se.toString());
        }
        return null;
    }

}