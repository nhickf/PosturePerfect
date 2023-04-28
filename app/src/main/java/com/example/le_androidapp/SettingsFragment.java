package com.example.le_androidapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SettingsFragment extends Fragment {

    Switch notificationSwitch;
    Switch phoneVibrateSwitch;
    Switch deviceVibrateSwitch;

    Button exportDownload;
    Button supportDevs;

    SharedPreferences sp;
    FragmentTransaction fr;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        notificationSwitch = (Switch) view.findViewById(R.id.setting_switch1);
        notificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notificationSwitch.isChecked())
                    Toast.makeText(getActivity(), "Notifications Turned On", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getActivity(), "Notifications Turned Off", Toast.LENGTH_SHORT).show();
            }
        });

        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        phoneVibrateSwitch = (Switch) view.findViewById(R.id.setting_switch2);
        phoneVibrateSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp = getActivity().getSharedPreferences("sharedData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                if(phoneVibrateSwitch.isChecked()) {
                    editor.putInt("phoneVibrate", 1);
                    v.vibrate(250);
                    //Toast.makeText(getActivity(), "Phone Vibration Turned On", Toast.LENGTH_SHORT).show();
                }
                else {
                    editor.putInt("phoneVibrate", 2);
                    //Toast.makeText(getActivity(), "Phone Vibration Turned Off", Toast.LENGTH_SHORT).show();
                }
                editor.commit();
            }
        });

        deviceVibrateSwitch = (Switch) view.findViewById(R.id.setting_switch3);
        deviceVibrateSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(deviceVibrateSwitch.isChecked())
                    Toast.makeText(getActivity(), "Device Vibration Turned On", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getActivity(), "Device Vibration Turned Off", Toast.LENGTH_SHORT).show();
            }
        });

        exportDownload = (Button) view.findViewById(R.id.export_button);
        exportDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Export and Download Information", Toast.LENGTH_SHORT).show();
            }
        });

        supportDevs = (Button) view.findViewById(R.id.dev_support);
        supportDevs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "The Devs Need Money", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.patreon.com"));
                startActivity(intent);
            }
        });
        return view;
    }
}