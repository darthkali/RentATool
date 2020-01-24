package de.rat.logistics;

import java.util.ArrayList;


/** Creates a bill .
 *  @param Stock is a array list of tools
 *
 */
public class Warehouse {
    private ArrayList<Tool> Stock= new ArrayList        <Tool>();


    public Warehouse() {
    }

    /** get  the stock from the warehouse and print the size
     *  @return  the stock
     *
     */
    public ArrayList<Tool> getStock() {
        System.out.println(Stock.size());
        return Stock;
    }

    /** put the respective tool in the warehouse
     *  @return  true if the add was ok
     *  @return  false if there was no tool to add
     *
     */
    public boolean putToolInWarehouse(Tool tool){

        if(tool !=null)
        {
            System.out.println("Das Tool ist im warehouse ");
            this.Stock.add(tool);
            return true;
        }
        return false;
    }



    /** remove the respective tool from the warehouse
     *  @return  the tool
     *  @return  null if there was no tool founded or a toll was not AVAILABLE
     *
     */
    public Tool removeToolFromWarehouse(Tool tool){
        for (Tool foundedTool : Stock)
        {
            if(foundedTool.equals(tool))
            {
                if(foundedTool.getToolStatus() == ToolStatus.AVAILABLE) {
                    Stock.remove(tool);
                    System.out.println("Das Tool ist bereit zum Ausleihen");
                    return tool;
                }
                //TODO: Fehlermeldung bei falschem Werkzeugstatus einbauen
            }
        }
        System.out.println("Werkzeug nicht im Lager vorhanden");
        return null;

    }

    /** set the tool status for the tool
     *  @return  true if the tool status was updated
     *  @return false if there was no tool
     *
     */
    public boolean setToolStatus(Tool tool, ToolStatus updatedToolStatus)
    {
        for(Tool foundedTool:Stock)
        {
            if(foundedTool.equals(tool))
            {
                foundedTool.setToolStatus(updatedToolStatus);
                return true;
            }
        }
        System.out.println("Werkzeug nicht im Lager vorhanden");
        return false;
    }





}
