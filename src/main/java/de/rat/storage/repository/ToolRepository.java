package de.rat.storage.repository;

import de.rat.model.logistics.Category;
import de.rat.model.logistics.ToolStatus;
import de.rat.model.logistics.Tool;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ToolRepository extends CrudRepository<Tool,Integer>
{

    Tool findById(int id);
    Tool findByDescription(String description);

    List<Tool> findByCategory(Category category);
    List<Tool> findByToolStatus(ToolStatus status);
    List<Tool> findByRentPrice(double rentPrice);

    //ToDO Query
    List<Tool> findByPrice(double lowRentPrice,double highRentPrice);


}

