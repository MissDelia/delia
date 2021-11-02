/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.widget.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * 一个简单的EditText监听器，仅监听afterTextChanged方法，简化代码逻辑
 * @author xiong'MissDelia'zhengkun
 * 2020/8/10 16:03
 */
public abstract class SimpleTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        onChange(s.toString());
    }

    public abstract void onChange(String str);
}
