package in.smartglobalsolutions.mygenerator.Searchspinner;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;



import androidx.fragment.app.FragmentManager;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.smartglobalsolutions.chitsoft.Adapter.CustomSeachAdapter;
import in.smartglobalsolutions.chitsoft.POJO.SeachPasser;
import in.smartglobalsolutions.chitsoft.R;

public class SearchableListDilog extends DialogFragment implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener,Filterable {

    private static final String ITEMS = "items";

    private ArrayAdapter listAdapter;

    private ListView _listViewItems;

    private SearchableItem _searchableItem;

    private OnSearchTextChanged _onSearchTextChanged;

    private SearchView _searchView;

    private String _strTitle;

    private String _strPositiveButtonText;

    private DialogInterface.OnClickListener _onClickListener;
    List items;
    public SearchableListDilog() {

    }



    public static SearchableListDilog newInstance(List items) {
        SearchableListDilog multiSelectExpandableFragment = new
                SearchableListDilog();

        Bundle args = new Bundle();
        args.putSerializable(ITEMS, (Serializable) items);

        multiSelectExpandableFragment.setArguments(args);

        return multiSelectExpandableFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Getting the layout inflater to inflate the view in an alert dialog.
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        // Crash on orientation change #7
        // Change Start
        // Description: As the instance was re initializing to null on rotating the device,
        // getting the instance from the saved instance
        if (null != savedInstanceState) {
            _searchableItem = (SearchableItem) savedInstanceState.getSerializable("item");
        }
        // Change End

        View rootView = inflater.inflate(R.layout.searchable_list_dialog, null);
        setData(rootView);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(rootView);

        String strPositiveButton = _strPositiveButtonText == null ? "CLOSE" : _strPositiveButtonText;
        alertDialog.setPositiveButton(strPositiveButton, _onClickListener);

        String strTitle = _strTitle == null ? "Select Item" : _strTitle;
        alertDialog.setTitle(strTitle);

        final AlertDialog dialog = alertDialog.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN);
        return dialog;
    }

    // Crash on orientation change #7
    // Change Start
    // Description: Saving the instance of searchable item instance.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("item", _searchableItem);
        super.onSaveInstanceState(outState);
    }
    // Change End

    public void setTitle(String strTitle) {
        _strTitle = strTitle;
    }

    public void setPositiveButton(String strPositiveButtonText) {
        _strPositiveButtonText = strPositiveButtonText;
    }

    public void setPositiveButton(String strPositiveButtonText, DialogInterface.OnClickListener onClickListener) {
        _strPositiveButtonText = strPositiveButtonText;
        _onClickListener = onClickListener;
    }

    public void setOnSearchableItemClickListener(SearchableItem searchableItem) {
        this._searchableItem = searchableItem;
    }

    public void setOnSearchTextChangedListener(OnSearchTextChanged onSearchTextChanged) {
        this._onSearchTextChanged = onSearchTextChanged;
    }

    private void setData(View rootView) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context
                .SEARCH_SERVICE);

        _searchView = (SearchView) rootView.findViewById(R.id.search);
        _searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName
                ()));

        _searchView.setIconifiedByDefault(false);
        _searchView.setOnQueryTextListener(this);
        _searchView.setOnCloseListener(this);
        _searchView.clearFocus();
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(_searchView.getWindowToken(), 0);


         items = (List) getArguments().getSerializable(ITEMS);

        _listViewItems = (ListView) rootView.findViewById(R.id.listItems);

        //create the adapter by passing your ArrayList data
        listAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,items);        //attach the adapter to the list
        _listViewItems.setAdapter(listAdapter);

        _listViewItems.setTextFilterEnabled(true);

        _listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _searchableItem.onSearchableItemClicked(listAdapter.getItem(position), position);
                getDialog().dismiss();
            }
        });
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        _searchView.clearFocus();
        return true;
    }


    @Override
    public boolean onQueryTextChange(String s) {
   //     listAdapter.filterData(s);

        if (TextUtils.isEmpty(s)) {
           listAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, items);        //attach the adapter to the list
            _listViewItems.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        } else {
          getFilter().filter(s);
        }
        if (null != _onSearchTextChanged) {
            _onSearchTextChanged.onSearchTextChanged(s);
        }
        if (s.length() == 0){

        }
        return true;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Object> results = new ArrayList<Object>();

                if (constraint != null) {
                    if (items != null && items.size() > 0) {
                        for ( Object g : items) {
                            if (g.toString().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                    oReturn.count =results.size();
                }
                return oReturn;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.count > 0) {
                  List  items1 = (ArrayList<Object>) results.values;
                    listAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, items1);        //attach the adapter to the list
                    _listViewItems.setAdapter(listAdapter);
                    listAdapter.notifyDataSetChanged();

                }else {
                    Log.i("TAG", "publishResults: "+"Empty");
                    List  items2 = new ArrayList();
                    listAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, items2);        //attach the adapter to the list
                    _listViewItems.setAdapter(listAdapter);
                    listAdapter.notifyDataSetChanged();
                }
            }
        };
    }


    public interface SearchableItem<T> extends Serializable {
        void onSearchableItemClicked(T item, int position);
    }

    public interface OnSearchTextChanged {
        void onSearchTextChanged(String strText);
    }
}
