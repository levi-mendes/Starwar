package br.com.levimendesestudos.starwars.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import br.com.levimendesestudos.starwars.R;
import br.com.levimendesestudos.starwars.activities.DetalhesActivity;
import br.com.levimendesestudos.starwars.model.Personagem;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 809778 on 03/02/2017.
 */

public class ListaPersonagensAdapter extends RecyclerView.Adapter<ListaPersonagensAdapter.ViewHolder> {

    private List<Personagem> mList;
    private Context mContext;

    public ListaPersonagensAdapter(Context context, List<Personagem> list) {
        mContext = context;
        mList    = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_personagens, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    /**
     *
     * Adiciona o item na posicao 0
     *
     * @param p
     *
     */
    public void adicionarItem(Personagem p) {
        mList.add(0, p);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Personagem p = mList.get(position);

        holder.itemView.setOnClickListener(view -> callDetalhes(p));
        holder.tvNome.setText(p.name);
        holder.tvUrl.setText(p.link);
    }

    private void callDetalhes(Personagem p) {
        Intent intent = new Intent(mContext, DetalhesActivity.class);
        intent.putExtra("personagem", p);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvNome)
        TextView tvNome;
        @BindView(R.id.tvUrl)
        TextView tvUrl;

        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);
        }
    }
}
