package android.example.passwordmanager;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        TextView editText0 = listItemView.findViewById(R.id.purpose);
        editText0.setText(currentItem.getPurpose());
        TextView editText1 = listItemView.findViewById(R.id.username);
        editText1.setText("Username: " + currentItem.getUsername());
        TextView editText2 = listItemView.findViewById(R.id.password);
        editText2.setText("Password: " + currentItem.getPassword());

        ImageView deleteButton = (ImageView) listItemView.findViewById(R.id.imageView);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(currentItem);
                notifyDataSetChanged();
                String user = currentItem.getUser();
                DatabaseReference fb = FirebaseDatabase.getInstance().getReference("savedInfo/" + user);
                fb.orderByChild("username").equalTo(currentItem.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("tesst", snapshot.toString());
                        snapshot.getChildren().iterator().next().getRef().removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // empty
                    }
                });
            }
        });

        return listItemView;
    }

}
