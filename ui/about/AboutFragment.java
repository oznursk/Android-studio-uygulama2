package com.example.myfinall.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.myfinall.R;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);



        ImageView profileImage = rootView.findViewById(R.id.profileImage);
        profileImage.setImageResource(R.drawable.profil);

        rootView.findViewById(R.id.linkedinLink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLinkedInProfile();
            }
        });

        rootView.findViewById(R.id.githubLink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGitHubProfile();
            }
        });

        return rootView;
    }

    public void openLinkedInProfile() {
        String linkedInUrl = "https://www.linkedin.com/in/oznur9";
        openUrl(linkedInUrl);
    }

    public void openGitHubProfile() {
        String githubUrl = "https://github.com/oznursk";
        openUrl(githubUrl);
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
