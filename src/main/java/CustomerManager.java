package main.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomerManager {
    private List<Customer> customers;

    public CustomerManager() {
        customers = new ArrayList<>();
    }

    public void addOrUpdateCustomer(Customer customer) {
        int index = findCustomerIndex(customer.getCustomerId());
        if (index != -1) {
            customers.set(index, customer);
        } else {
            customers.add(customer);
        }
    }

    public void promoteCustomer(Customer customer) {
        if (customer instanceof Guest) {
            Guest guest = (Guest) customer;
            if (guest.getBorrowedItemsCount() > 3) {
                Regular regular = new Regular(customer.getName(), customer.getCustomerId(), customer.getPhone(), customer.getAddress(), customer.getNumberOfRentals(), customer.getCustomerType(), customer.getUsername(), customer.getPassword());
                customers.remove(customer);
                customers.add(regular);
            }
        } else if (customer instanceof Regular) {
            Regular regular = (Regular) customer;
            if (regular.getBorrowedItemsCount() > 5) {
                VIP vip = new VIP(customer.getName(), customer.getCustomerId(), customer.getPhone(), customer.getAddress(), customer.getNumberOfRentals(), customer.getCustomerType(), customer.getUsername(), customer.getPassword());
                customers.remove(customer);
                customers.add(vip);
            }
        }
    }

    public List<Customer> getAllCustomersSortedByName() {
        List<Customer> sortedCustomers = new ArrayList<>(customers);
        sortedCustomers.sort(Comparator.comparing(Customer::getName));
        return sortedCustomers;
    }

    public List<Customer> getCustomersByLevel(String level) {
        List<Customer> filteredCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            if ((level.equals("Guest") && customer instanceof Guest)
                    || (level.equals("Regular") && customer instanceof Regular)
                    || (level.equals("VIP") && customer instanceof VIP)) {
                filteredCustomers.add(customer);
            }
        }
        return filteredCustomers;
    }

    public Customer searchCustomerByNameOrId(String query) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(query) || String.valueOf(customer.getCustomerId()).equals(query)) {
                return customer;
            }
        }
        return null;
    }

    private int findCustomerIndex(int customerId) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCustomerId() == customerId) {
                return i;
            }
        }
        return -1;
    }
}
