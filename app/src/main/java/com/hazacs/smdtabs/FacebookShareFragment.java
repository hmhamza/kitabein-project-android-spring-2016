package com.hazacs.smdtabs;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;


public class FacebookShareFragment extends Fragment {


    public FacebookShareFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facebook_share, container, false);

        return view;
    }


    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        ShareButton fbShareButton = (ShareButton) view.findViewById(R.id.fb_share_button);
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle("FAST-NUCES")
                .setImageUrl(Uri.parse("https://upload.wikimedia.org/wikipedia/en/e/e4/National_University_of_Computer_and_Emerging_Sciences_logo.png"))
                .setContentDescription("My University")
                .setContentUrl(Uri.parse("http://nu.edu.pk/"))
                .build();
        fbShareButton.setShareContent(linkContent);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

     }


}
