package com.example.githubuser.ui.detail.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.githubuser.R;
import com.example.githubuser.database.local.entity.UserGitEntity;
import com.example.githubuser.databinding.ActivityDetailUserBinding;
import com.example.githubuser.databinding.ItemListviewBinding;

public class DetailUserAdapter  extends ListAdapter<UserGitEntity, DetailUserAdapter.MyViewHolder> {
    private final OnItemClickCallback onItemClickCallback;

    interface OnItemClickCallback {
        void onBookmarkClick(UserGitEntity data);
    }

    public DetailUserAdapter(OnItemClickCallback onItemClickCallback) {
        super(DIFF_CALLBACK);
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityDetailUserBinding binding = ActivityDetailUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailUserAdapter.MyViewHolder holder, int position) {
        UserGitEntity userGitEntity = getItem(position);
        holder.bind(userGitEntity);
    }



    static class MyViewHolder extends RecyclerView.ViewHolder {

        final ActivityDetailUserBinding binding;

        MyViewHolder(ActivityDetailUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(UserGitEntity userGitEntity) {
            binding.tvnamaUser.setText(userGitEntity.getNamaUser());
            Toast.makeText(itemView.getContext(),userGitEntity.getNamaUser(),Toast.LENGTH_LONG).show();
            Toast.makeText(itemView.getContext(),"anu",Toast.LENGTH_LONG).show();
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
