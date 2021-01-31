package utils;


import static utils.Constants.LINES_DISPLAY_PER_PAGE;

public class Paginator {
    public int getNumberOfPages(int number){
        int pages = 0;
        if(number > 0){
            pages = number / LINES_DISPLAY_PER_PAGE;
            if(number % LINES_DISPLAY_PER_PAGE != 0){
                pages+=1;
            }
        }
        return pages;
    }
}
