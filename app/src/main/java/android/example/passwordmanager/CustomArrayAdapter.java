package android.example.passwordmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<Information> {

    public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Information> informationList) {
        super(context, resource, informationList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list, parent, false);
        }
        Information currentItem = getItem(position);

        EditText editText0 = listItemView.findViewById(R.id.purpose);
        editText0.setText(currentItem.getPurpose());
        EditText editText1 = listItemView.findViewById(R.id.username);
        editText1.setText(currentItem.getPassword());
        EditText editText2 = listItemView.findViewById(R.id.password);
        editText2.setText(currentItem.getUsername());

        return listItemView;
    }
}
