package khairy.com.bakingapp;

import com.bumptech.glide.load.engine.Resource;

import java.util.ArrayList;
import java.util.List;

import khairy.com.bakingapp.R;

public class imgPreview {
    private ArrayList<Integer> img;

    public imgPreview() {
        img = new ArrayList<Integer>();
        img.add(R.drawable.nutella) ;
        img.add(R.drawable.brow);
        img.add(R.drawable.yellow);
        img.add(R.drawable.cheese);
    }

    public ArrayList<Integer> getImg() {
        return img;
    }

}
