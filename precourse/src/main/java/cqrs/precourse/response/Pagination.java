package cqrs.precourse.response;

public class Pagination {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private int perPage;

    public Pagination(int totalItems, int totalPages, int currentPage, int perPage) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.perPage = perPage;
    }
}
