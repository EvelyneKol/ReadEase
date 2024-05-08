package com.example.readease3.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.readease3.DBHandler;
import com.example.readease3.databinding.SearchBinding;

import android.widget.SearchView;
import com.example.readease3.Book;
import java.util.List;
import android.widget.Button;

public class search_fragment extends Fragment {

    private SearchBinding binding;
    private DBHandler dbHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = SearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Instantiate the DBHandler class
        dbHandler = new DBHandler(requireContext());

        // Handle search button click
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                searchBooks(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle text changes in the search view (optional)
                return false;
            }
        });

        return root;
    }

    private void searchBooks(String query) {
        // Perform search query in the database
        List<Book> books = dbHandler.searchBooksByTitle(query);

        // Display search results
        if (!books.isEmpty()) {
            // Display the first matching book details
            Book book = books.get(0);
            String bookDetails = "Title: " + book.getTitle() + "\n" +
                    "ISBN: " + book.getIsbn() + "\n" +
                    "Pages: " + book.getPages();

            // Update UI with book details
            binding.searchResultTextView.setText(bookDetails);

            // Adjust button visibility
            binding.buyButton.setVisibility(View.VISIBLE);
            binding.borrowButton.setVisibility(View.VISIBLE);
            binding.reviewButton.setVisibility(View.VISIBLE);

        } else {
            // No matching books found
            binding.searchResultTextView.setText("No matching books found.");

            // Hide buttons if no book is found
            binding.buyButton.setVisibility(View.GONE);
            binding.borrowButton.setVisibility(View.GONE);
            binding.reviewButton.setVisibility(View.GONE);

        }
    }







    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
