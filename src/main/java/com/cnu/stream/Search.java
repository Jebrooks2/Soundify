package com.cnu.stream;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;


public class Search extends AppCompatActivity {

    private EditText mSearchField;
    private RecyclerView mResultList;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabaseReference;
    int i;
    ListView listView;
    JcPlayerView jcPlayerView;
    List<JcAudio> jcAudios;
    //private final View.OnClickListener mOnClickListener = new MyOnClickListener();
    private RecyclerView recyclerView;
    private SongAdapter adapter; // Create Object of the Adapter class
    private DatabaseReference mbase; // Create object of the
    // Firebase Realtime Database

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.layout_search);
        recyclerView = findViewById(R.id.result_list);
        Button backMain = findViewById(R.id.Back);
        mSearchField = (EditText) findViewById(R.id.search_here);
        jcAudios = new ArrayList<>();
        ImageButton mSearchBtn = (ImageButton) findViewById(R.id.search_btn);
        listView = findViewById(R.id.songsList);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("songs");
        //recyclerView.setHasFixedSize(true);
        System.out.println("WTFFFFF");
        jcPlayerView = findViewById(R.id.jcplayer);
        jcPlayerView.setVisibility(View.INVISIBLE);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /*recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return true;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                System.out.println("AWEFAWE");
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });*/

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();
                firebaseSongSearch(searchText);
            }
        });

        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain();
            }
        });


    }

    private void firebaseSongSearch(String searchText) {

        Toast.makeText(Search.this, "Started Search", Toast.LENGTH_SHORT).show();

        Query fireQuery = mDatabaseReference.orderByChild("songName").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerOptions<Song> options =
                new FirebaseRecyclerOptions.Builder<Song>()
                        .setQuery(fireQuery, Song.class)
                        .setLifecycleOwner(this)
                        .build();
        adapter = new SongAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.getChildAt(fireQuery);
        adapter.startListening();
        System.out.println(adapter);
        //int size = ((SongAdapter) recyclerView.getAdapter()).getItemCount();
        // Do something with the item like save it to a selected items array.
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
   /* @Override*/
    /*protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }*/



        /*FirebaseRecyclerAdapter<Song, SongViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Song, SongViewHolder>(options) {

            @NonNull
            @Override
            public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new SongViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_search, parent, false));

            }

            @Override
            protected void onBindViewHolder(@NonNull final SongViewHolder holder, int position, @NonNull Song model) {

                holder.setDetails(getApplicationContext(), model.getSongArtist(), model.getSongName(), model.getSongDuration(), model.getImageUrl());

            }
        };*/
        //System.out.println(firebaseRecyclerAdapter);
      // mResultList.setAdapter(firebaseRecyclerAdapter);

    private void backToMain() {
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
        finish();
    }

    /*public static class SongViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public SongViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setDetails(Context ctx, String songName, String songDuration, String artistName, String imageUrl) {

            TextView song_name = (TextView) mView.findViewById(R.id.songName);
            TextView song_duration = (TextView) mView.findViewById(R.id.songDuration);
            TextView artist_name = (TextView) mView.findViewById(R.id.artistName);
            ImageView image_url = (ImageView) mView.findViewById(R.id.image);


            song_name.setText(songName);
            song_duration.setText(songDuration);
            artist_name.setText(artistName);
            Glide.with(ctx).load(imageUrl).into(image_url);

        }}*/
    class SongAdapter extends FirebaseRecyclerAdapter<
            Song, SongAdapter.songsViewholder> {



        public SongAdapter(
                @NonNull FirebaseRecyclerOptions<Song> options)
        {
            super(options);
            Log.d("Options"," data : "+options);
        }

        // Function to bind the view in Card view(here
        // "person.xml") iwth data in
        // model class(here "person.class")
        @Override
        protected void
        onBindViewHolder(@NonNull SongAdapter.songsViewholder holder,
                         int position, @NonNull Song model)

        {

            System.out.println("WTFGWE");
            final String[] yo = new String[1];
            final String post_key = getRef(position).getKey().toString();
            // Add firstname from model class (here
            // "person.class")to appropriate view in Card
            // view (here "person.xml")
            holder.songName.setText(model.getSongArtist());

            // Add lastname from model class (here
            // "person.class")to appropriate view in Card
            // view (here "person.xml")
            holder.artistName.setText(model.getSongDuration());

            // Add age from model class (here
            // "person.class")to appropriate view in Card
            // view (here "person.xml")
            holder.Duration.setText(model.getSongName());

            // return model.getSongUrl();

            yo[0] = model.getSongUrl();
            //jcAudios.clear();



            holder.Url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   /* if (jcAudios.size() == 0) {
                        jcPlayerView.setVisibility(View.INVISIBLE);
                        Toast.makeText(Search.this, "Search for a Song", Toast.LENGTH_LONG).show();
                        System.out.println("FU");
                    }*/

                  // else {
                            yo[0] = model.getSongUrl();
                            System.out.println(yo[0]);
                            System.out.println(model.getSongName());
                            jcAudios.add(JcAudio.createFromURL(model.getSongName(), yo[0]));
                            jcPlayerView.initPlaylist(jcAudios, null);
                            jcPlayerView.playAudio(jcAudios.get(i));
                            jcPlayerView.setVisibility(View.VISIBLE);
                            jcPlayerView.createNotification();

                            //adapter.notifyDataSetChanged();
                      //  }
                        }
            });
            //jcPlayerView.setJcPlayerManagerListener(this);
        }

        // Function to tell the class about the Card view (here
        // "person.xml")in
        // which the data will be shown
        @NonNull
        @Override
        public SongAdapter.songsViewholder
        onCreateViewHolder(@NonNull ViewGroup parent,
                           int viewType)

        {

            View view
                    = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.song_layout, parent, false);
            return new SongAdapter.songsViewholder(view);
        }

        // Sub Class to create references of the views in Crad
        // view (here "person.xml")
        class songsViewholder
                extends RecyclerView.ViewHolder {

            TextView songName, artistName, Duration, Url;
            public songsViewholder(@NonNull View itemView)
            {

                super(itemView);

                artistName
                        = itemView.findViewById(R.id.textView1);
                songName = itemView.findViewById(R.id.textView2);
                Duration = itemView.findViewById(R.id.textView3);
                Url = itemView.findViewById(R.id.textView4);
                //jcPlayerView = itemView.findViewById(R.id.jcplayer);
            }}

    }}





