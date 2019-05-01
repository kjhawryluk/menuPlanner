package edu.uchicago.kjhawryluk.prolistview.Listeners;

import android.view.KeyEvent;
import android.view.View;

public class EditTextEnterListener implements View.OnKeyListener {

    View view;

    public EditTextEnterListener(View view) {
        this.view = view;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            view.clearFocus();
        }
        return true;
    }
}
