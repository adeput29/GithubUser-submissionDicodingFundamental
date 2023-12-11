package com.example.githubuser.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.githubuser.R;
import com.example.githubuser.database.local.entity.UserGitEntity;
import com.example.githubuser.database.local.entity.UserGitSelect;
import com.example.githubuser.databinding.FragmentMainBinding;
import com.example.githubuser.databinding.ItemListviewBinding;
import com.example.githubuser.ui.detail.DetailUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainUserGitAdapter extends ListAdapter<UserGitEntity, MainUserGitAdapter.MyViewHolder> {
    private final MainUserGitAdapter.OnItemClickCallback onItemClickCallback;

    interface OnItemClickCallback {
        void onBookmarkClick(UserGitEntity data);
    }

    public MainUserGitAdapter(MainUserGitAdapter.OnItemClickCallback onItemClickCallback) {
        super(DIFF_CALLBACK);
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public MainUserGitAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListviewBinding binding = ItemListviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MainUserGitAdapter.MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MainUserGitAdapter.MyViewHolder holder, int position) {
        UserGitEntity userGitEntity = getItem(position);
        holder.bind(userGitEntity);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        final ItemListviewBinding binding;

        MyViewHolder(ItemListviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(UserGitEntity userGitEntity) {
            binding.tvNamaUser.setText(userGitEntity.getNamaUser());
            binding.tvKeterangan.setText("ID: "+userGitEntity.getId());
            Glide.with(itemView.getContext())
                    .load(userGitEntity.getAvatar_url())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(binding.imgUser);
            itemView.setOnClickListener(v -> {
                UserGitSelect userGitSelect = new UserGitSelect();
                userGitSelect.setId(userGitEntity.getId());
                userGitSelect.setNamaUser(userGitEntity.getNamaUser());
                userGitSelect.setUserName(userGitEntity.getUsername());
                userGitSelect.setFollowers(userGitEntity.getFollowers_url());
                userGitSelect.setFollowing(userGitEntity.getFollowing_url());
                userGitSelect.setAvatar_url(userGitEntity.getAvatar_url());
                Intent moveWithObjectIntent = new Intent(itemView.getContext(), DetailUser.class);
                moveWithObjectIntent.putExtra(DetailUser.EXTRA_USER, userGitSelect);
                itemView.getContext().startActivity(moveWithObjectIntent);
            });
        }
    }

    public static final DiffUtil.ItemCallback<UserGitEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<UserGitEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull UserGitEntity oldUser, @NonNull UserGitEntity newUser) {
                    return oldUser.getNamaUser().equals(newUser.getNamaUser());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull UserGitEntity oldUser, @NonNull UserGitEntity newUser) {
                    return oldUser.equals(newUser);
                }
            };
}

