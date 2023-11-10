package com.example.spellviewer;

import static com.example.spellviewer.CharacterCreationActivity.drawableToBitmap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageFragment extends Fragment {

    // the fragment initialization parameter for character types to display
    private static final String ARG_TYPE = "characterType";

    private String mType;
    private CheckImageView selectedImageView;

    public ImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter ARG_TYPE.
     * @return A new instance of fragment imageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImageFragment newInstance(String param1) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment_image, container, false);

        return initImages(fragmentLayout,inflater);
    }

    /**
     * Method to fill the table layout with images according to the tab
     * @param fragmentLayout Layout containing a table layout that is filled
     * @param inflater Inflater that is used to inflate the table rows
     * @return Returns the view the complete Fragment view
     */
    private View initImages(View fragmentLayout, LayoutInflater inflater) {
//        Number of images
        final int nImg;
        switch (mType) {
            case "male":
                nImg = 32;
                break;
            case "female":
                nImg = 31;
                break;
            case "other":
                nImg = 4;
                break;
            default:
                nImg = 0;
                break;
        }
//        Number of rows needed, Number of images / 2 rounded up
        final int nRow = (nImg + 1) / 2;
//        Store resources in array
        int[] resourceArr = new int[nImg];
        for (int i = 0; i < nImg; i++) {
            resourceArr[i] = getResources().getIdentifier(mType+"image"+(i+1),"drawable","com.example.spellviewer");
        }
        View.OnClickListener onClickListener = v -> imageViewSelected((CheckImageView) v);
//        Create rows and add them to the passed layout
        TableLayout tableLayout = fragmentLayout.findViewById(R.id.tableLayout);
        for (int i = 0; i < nRow; i++) {
            View newRow = inflater.inflate(R.layout.image_row, null);
            for (int j = 0, columnCount = ((TableRow) newRow).getChildCount(); j < columnCount; j++) {
                View imageView = ((TableRow) newRow).getChildAt(j);
//                In case of odd number images make sure to stay in bounds of array
                if (imageView instanceof CheckImageView && i*2+j < nImg) {
//                    Set on click listener and image for the image views
                    imageView.setOnClickListener(onClickListener);
                    ((CheckImageView) imageView).setImageResource(resourceArr[i*2+j]);
                }
            }
//            the row is added to the table
            tableLayout.addView(newRow);
        }
        return fragmentLayout;
    }

    /**
     * Method to select or unselect a CheckImageView
     * @param imageView Image view that is un-/selected
     */
    private void imageViewSelected(CheckImageView imageView){
        if (selectedImageView == imageView) {
//            The selected ImageView is clicked again, and hence it is set to unselected.
            imageView.toggle();
            selectedImageView = null;
            return;
        }
        if (selectedImageView != null) {
//            If a different image was selected previously it is unselected here
            selectedImageView.toggle();
        }
//        Select the image and save selection.
        imageView.toggle();
        selectedImageView = imageView;
    }

    public void unselectImageView() {
        if (selectedImageView == null) {
//            if no image was selected, there is nothing to do
            return;
        }
        selectedImageView.toggle();
        selectedImageView = null;
    }

    public SerialBitmap getSelectedImage() {
        return selectedImageView == null ? null : new SerialBitmap(drawableToBitmap(selectedImageView.getDrawable()));
    }
}