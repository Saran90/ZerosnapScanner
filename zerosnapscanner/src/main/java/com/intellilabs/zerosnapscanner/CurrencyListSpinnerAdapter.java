package com.intellilabs.zerosnapscanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.intellilabs.zerosnapscanner.getcurrencies.GetCurrenciesResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * The CaregiverListSpinnerAdapter is the adapter used to attach list of caregiver's assigend to
 * the user to the spinner.
 *
 * @author  Mary Mathew
 * @version 1.0
 */
public class CurrencyListSpinnerAdapter extends BaseAdapter {

    private List<GetCurrenciesResponse.Data> currencies =  new ArrayList<>();
    private Context context;

    public CurrencyListSpinnerAdapter(Context context, List<GetCurrenciesResponse.Data> currencies){
        this.context = context;
        this.currencies = currencies;
    }

    @Override
    public int getCount() {
        return currencies.size();
    }

    @Override
    public Object getItem(int i) {
        return currencies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.item_currency,viewGroup,false);

        GetCurrenciesResponse.Data model = currencies.get(i);

        TextView mCaregiverName = (TextView) view.findViewById(R.id.tv_currency_name);
        mCaregiverName.setText(model.getCurrencyName());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.item_currency_drop_down,parent,false);
        GetCurrenciesResponse.Data model = currencies.get(position);

        TextView mCaregiverName = (TextView) convertView.findViewById(R.id.tv_currency_name);
        mCaregiverName.setText(model.getCurrencyName());

        return convertView;
    }
}
