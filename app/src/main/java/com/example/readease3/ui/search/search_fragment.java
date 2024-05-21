package com.example.readease3.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.readease3.review;
import com.example.readease3.ad_result;

import com.example.readease3.DBHandler;
import com.example.readease3.R;
import com.example.readease3.borrow_add;
import com.example.readease3.databinding.SearchBinding;
import android.widget.SearchView;
import com.example.readease3.Book;
import com.example.readease3.events_screen;
import com.example.readease3.participants_list;

import java.util.List;



public class search_fragment extends Fragment {

    private SearchBinding binding;
    private DBHandler dbHandler;
    private Button buttonBuy;
    private Button buttonBorrow;
    private Button reviewButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = SearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        buttonBuy = root.findViewById(R.id.buyButton);
        reviewButton = root.findViewById(R.id.reviewButton);
        buttonBorrow = root.findViewById(R.id.borrowButton);
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to adresult
                Navigation.findNavController(v).navigate(R.id.action_searchFragment_to_adresult);
            }
        });
        buttonBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to EbookFormActivity
                Navigation.findNavController(v).navigate(R.id.action_searchFragment_to_adresult);
            }
        });

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to EbookFormActivity
                Navigation.findNavController(v).navigate(R.id.action_searchFragment_to_review);

            }
        });

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

    private String searchedBookISBN; // Class variable to store the ISBN of the searched book

    private void searchBooks(String query) {
        // Perform search query in the database
        List<Book> books = dbHandler.searchBooksByTitle(query);

        // Display search results
        if (!books.isEmpty()) {
            // Display the first matching book details
            Book book = books.get(0);
            searchedBookISBN = book.getIsbn(); // Store the ISBN of the searched book

            // Update UI with book details
            String bookDetails = "Title: " + book.getTitle() + "\n" +
                    "ISBN: " + searchedBookISBN + "\n" +  // Use searchedBookISBN here
                    "Pages: " + book.getPages();
            binding.searchResultTextView.setText(bookDetails);

            // Adjust button visibility
            binding.buyButton.setVisibility(View.VISIBLE);
            binding.borrowButton.setVisibility(View.VISIBLE);
            binding.reviewButton.setVisibility(View.VISIBLE);

            reviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create an Intent to start the review activity
                    Intent intent = new Intent(requireContext(), review.class);

                    // Pass the ISBN of the searched book as an extra
                    intent.putExtra("searched_book_isbn", searchedBookISBN);

                    // Start the review activity
                    startActivity(intent);
                }
            });
            buttonBuy.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ad_result.class);
                intent.putExtra("action", "buy");
                intent.putExtra("searched_book_isbn", searchedBookISBN); // pass the ISBN as well
                startActivity(intent);
            });


            buttonBorrow.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ad_result.class);
                intent.putExtra("action", "borrow");
                intent.putExtra("searched_book_isbn", searchedBookISBN); // pass the ISBN as well
                startActivity(intent);
            });


        }else {
            // No matching books found
            binding.searchResultTextView.setText("Δεν βρέθηκαν βιβλία με αυτόν τον τίτλο.");

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

