package com.example.pranav.playmusic;

import android.Manifest;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    RecyclerView lvPlaylist;
    MusicListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA, getWindow());
        lvPlaylist = (RecyclerView) findViewById(R.id.lvPlaylist);
        //  lvPlaylist = (ListView) findViewById(R.id.lvPlaylist);

        Perman.askForPermission(ListActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                new Perman.OnPermissionRequestResult() {
                    @Override
                    public void onGranted(String permission) {

                        RunningMusic();

                    }

                    @Override
                    public void onDenied(String permission) {
                        onDestroy();
                    }
                });
    }

    public void RunningMusic() {
        final ArrayList<File> mySong = findSongs(Environment.getExternalStorageDirectory());

        mListAdapter = new MusicListAdapter(mySong, this);
        lvPlaylist.setLayoutManager(new LinearLayoutManager(this));

        lvPlaylist.setAdapter(mListAdapter);
        lvPlaylist.addItemDecoration(new SimpleDividerItemDecoration(this));

//        ArrayAdapter<File> myAdapter = new ArrayAdapter<File>(this,android.R.layout.simple_list_item_1,android.R.id.text1,mySong);
//        lvPlaylist.setAdapter(myAdapter);
//        lvPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(getApplicationContext(),Player.class).putExtra("pos",position).putExtra("MySong",mySong));
//            }
//        });
    }

    public ArrayList<File> findSongs(File root) {
        ArrayList<File> al = new ArrayList<File>();

        File[] files = root.listFiles();

        for (File singleFile : files) {
            if (singleFile.isDirectory()) {
                al.addAll(findSongs(singleFile));
            } else {
                if (singleFile.getName().endsWith(".mp3")) {
                    al.add(singleFile);
                }
            }
        }

        return al;
    }
}
