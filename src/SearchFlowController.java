import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

/****************************************************************************
* Name:    SearchContoller
* Purpose:  The search controller will handle any user interaction
* within the SearchFlow component of the application. This will involve
* interactions with Search and Flight objects.  SearchController will
* create a Basic search object from inputs on the SearchFlowView to build
* a query, collect Flight objects from the DB and pass those Flight objects
* back to the user trough the SearchFlowView formatting.
*
******************************************************************************/
public class SearchFlowController {
    SearchFlowView search_view;
    Container parent_container;
    Travlr parent_frame;
    SearchModel search_model;
    String[] locations;
    ArrayList<Map> flight_data;
    Flight individual_flight;

    /*******************************************************************
    * Name:    SearchFlowController()    :   Constructor               *
    * Purpose:                                                         *
    * Params:                                                          *
    * Postcondition:                                                   *
    *      SearchFlowController() object has been created.             *
    ********************************************************************/
    public SearchFlowController(Travlr pframe, Container pcontain ){
        parent_frame = pframe;
        parent_container = pcontain;
        search_model = new SearchModel();
        locations = search_model.getAllAirports();
        search_view = new SearchFlowView();
        search_view.setAirports(locations);
        search_view.updateView();
        addControls();
        parent_container.add(search_view, search_view.getConstraints());
    }

    /*******************************************************************
    * Name:    updateView()   :   Method                               *
    * Purpose:                                                         *
    ********************************************************************/
    public void addControls() {
        search_view.search_submit_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performBasicSearch();
            }
        });

        for (int i = 0; i < search_view.booking_buttons.length; i++){
            final int j = i;
            System.out.println("Adding event to button "+Integer.toString(j));
            search_view.booking_buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("Starting Bookingsflow");
                    parent_frame.startBookingsFlow(search_view.getFlightData().get(j));
                }
            });
        }
    }

    private void setNewDisplay(String new_view) {
        parent_container.remove(search_view);
        search_view.setDisplay(new_view);
        parent_container.add(search_view);
        parent_container.validate();
        parent_container.repaint();
    }

    private void performBasicSearch(){
        String src = search_view.getSrc();
        String dest = search_view.getDest();
        String date = search_view.getDate();
        String time = search_view.getTime();
        search_model = new SearchModel(src, dest, date, time);
        flight_data = search_model.getFlightData();
        search_view.setFlightData(flight_data);
        search_view.setDisplay("list");
        addControls();
    }

    /** Getter/Setter functions for updating individual fields of a Search object. */
    public void getSearchDestination( SearchModel search_model ){}
    public void setSeachDestination() {}
    public void getSearchSource() {}
    public void setSeachSource(String src) {}
    public void getTime() {}
	public void setTime() {}
	public void getDate() {}
	public void setDate() {}
	public void getAvailableSeats() {}
	public void setAvailableSeats() {}

}