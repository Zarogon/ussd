package com.tooploox.ussd.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tooploox.ussd.BR;
import com.tooploox.ussd.R;
import com.tooploox.ussd.domain.Ussd;
import com.tooploox.ussd.utils.ItemViewPositionProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Maksym Dybarskyi | maksym.dybarskyi@tooploox.com
 * 01/03/2017 16:35
 */
class UssdListAdapter extends RecyclerView.Adapter<UssdListAdapter.Holder> implements UssdViewModel {

    class Holder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        Holder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private List<Ussd> data = new ArrayList<>();
    private ItemViewPositionProvider viewPositionProvider;
    private UssdListActivity.UiEventsReactor uiEventsReactor;

    public UssdListAdapter(ItemViewPositionProvider viewPositionProvider, UssdListActivity.UiEventsReactor uiEventsReactor) {
        this.viewPositionProvider = viewPositionProvider;
        this.uiEventsReactor = uiEventsReactor;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.ussd_list_item, parent, false);
        binding.getRoot().setOnClickListener(view -> {
            Ussd ussd = getUssdByPosition(viewPositionProvider.getPosition(view));
            uiEventsReactor.onUssdItemClicked(ussd);
        });
        return new Holder(binding);

    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.binding.setVariable(BR.ussd, getUssdByPosition(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void setDataAndInvalidateView(List<Ussd> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    private Ussd getUssdByPosition(int position) {
        return data.get(position);
    }
}
