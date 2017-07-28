package flatfinder.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import flatfinder.domain.Room;

public interface RoomRepository extends CrudRepository<Room, Integer> {
  public Room findById(Integer id);
  public List<Room> findByPropertyId(Integer propertyId);
  
}
