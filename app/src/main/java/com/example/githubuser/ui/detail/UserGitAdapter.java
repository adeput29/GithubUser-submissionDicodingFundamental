package com.example.githubuser.ui.detail;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.githubuser.databinding.FragmentUserBinding;
import com.example.githubuser.databinding.ItemListviewBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class UserGitAdapter extends ListAdapter<UserGitEntity, UserGitAdapter.MyViewHolder> {
    private final OnItemClickCallback onItemClickCallback;

    interface OnItemClickCallback {
        void onBookmarkClick(UserGitEntity data);
    }

    public UserGitAdapter(OnItemClickCallback onItemClickCallback) {
        super(DIFF_CALLBACK);
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListviewBinding binding = ItemListviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserGitEntity userGitEntity = getItem(position);
        holder.bind(userGitEntity);

        /*ivBookmark.setOnClickListener(view -> {
            onItemClickCallback.onBookmarkClick(userGitEntity);
        });*/
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
