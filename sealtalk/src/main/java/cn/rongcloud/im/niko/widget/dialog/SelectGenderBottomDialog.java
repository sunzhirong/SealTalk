package cn.rongcloud.im.niko.widget.dialog;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.rongcloud.im.R;
import com.googlecode.mp4parser.boxes.MLPSpecificBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SelectGenderBottomDialog extends BaseBottomDialog {

    private static OnSelectPictureListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_picture, null);
        Button btnTop = (Button) view.findViewById(R.id.btn_take_picture);
        btnTop.setText(R.string.man);
        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onSelectGender(true);
                }
            }
        });
        Button btnBottom = (Button) view.findViewById(R.id.btn_album);
        btnBottom.setText(R.string.women);
        btnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onSelectGender(false);
                }
            }
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }



    public static class Builder {

        public void setOnSelectPictureListener(OnSelectPictureListener l) {
            listener = l;
        }

        public SelectGenderBottomDialog build() {
            SelectGenderBottomDialog dialog = getCurrentDialog();
            return dialog;
        }


        protected SelectGenderBottomDialog getCurrentDialog() {
            return new SelectGenderBottomDialog();
        }
    }

    /**
     * 选择图片回调结果
     */
    public interface OnSelectPictureListener {
        void onSelectGender(boolean isMan);
    }


}
