package com.example.bucketlist;

import android.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.bucketlist.BucketListRoomDatabase;
import com.example.bucketlist.R;
import com.example.bucketlist.BucketList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    int REQUEST_CODE = 1234;
    private List<BucketList> mBucketList;
    private BucketListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private GestureDetector mGestureDetector;
    private LinearLayoutManager linearLayoutManager;

    private BucketListRoomDatabase db;
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = BucketListRoomDatabase.getDatabase(this);
        mBucketList = new ArrayList<>();
        mAdapter = new BucketListAdapter(mBucketList);

        mRecyclerView = findViewById(R.id.rvBucketList);
        mRecyclerView.setAdapter(mAdapter);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    int adapterPosition = mRecyclerView.getChildAdapterPosition(child);
                    deleteBucketList(mBucketList.get(adapterPosition));
                }
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BucketListCreate.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        //Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());
                        deleteBucketList(mBucketList.get(position));
                        mBucketList.remove(position);
                        mAdapter.notifyItemRemoved(position);
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                mGestureDetector.onTouchEvent(motionEvent);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
        getAllBucketLists();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // Checking the item id of our menu item.

        if (item.getItemId() == R.id.action_delete_item) {

            // Deleting all items and notifying our list adapter of the changes.

            deleteAllProducts(mBucketList);

            return true;

        }


        return super.onOptionsItemSelected(item);

    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String title = data.getStringExtra(BucketListCreate.nTitle);
                String description = data.getStringExtra(BucketListCreate.nDescription);
                BucketList bucketList = new BucketList(title, description);
                insertBucketList(bucketList);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
                updateUI();
            }
        }

    }

    private void deleteAllProducts(final List<BucketList> products) {

        executor.execute(new Runnable() {

            @Override

            public void run() {

                db.bucketListDao().deleteBucketList(products);

                getAllBucketLists();

            }
        });
    }

    private void getAllBucketLists() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mBucketList = db.bucketListDao().getAllBucketLists();

                // In a background thread the user interface cannot be updated from this thread.
                // This method will perform statements on the main thread again.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI();
                    }
                });
            }
        });
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new BucketListAdapter(mBucketList);
            mRecyclerView.setAdapter(mAdapter);
        }
//        } else {
//            mAdapter.swapList(mBucketList);
//        }
    }


    private void insertBucketList(final BucketList bucketList) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketListDao().insertBucketList(bucketList);
                getAllBucketLists(); // Because the Room database has been modified we need to get the new list of reminders.
            }
        });
    }

    private void updateBucketList(final BucketList bucketList) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketListDao().updateBucketList(bucketList);
                getAllBucketLists(); // Because the Room database has been modified we need to get the new list of reminders.
            }
        });
    }

    private void deleteBucketList(final BucketList bucketList) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketListDao().deleteBucketList(bucketList);
                getAllBucketLists(); // Because the Room database has been modified we need to get the new list of reminders.
            }
        });
    }

}
