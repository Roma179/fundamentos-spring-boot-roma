package com.fundamentosspring.springboot.fundamentos.repository;

import com.fundamentosspring.springboot.fundamentos.dto.UserDto;
import com.fundamentosspring.springboot.fundamentos.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {

    @Query("Select u from User u Where u.email=?1 ")
    //optiona hace uso mejor de los null
    Optional<User> findByUserEmail(String email);

    @Query("Select u from User u where u.name like ?1%")
    List<User> findAndSort(String name, Sort sort);

    List<User> findByName(String name);

    Optional<User> findByEmailAndName(String email,String name);

    List<User> findByNameLike(String name);

    List<User> findByNameOrEmail(String name, String email);

    List<User> findByBirthDateBetween(LocalDate begin, LocalDate end);

    //List<User> findByNameLikeOrderByIdDesc(String name);

    List<User> findByNameContainingOrderByIdDesc(String name);

    @Query("SELECT new com.fundamentosspring.springboot.fundamentos.dto.UserDto(u.id,u.name,u.birthDate)" +
            " from User u " +
            " where u.birthDate=:parametroFecha " +
            " and u.email=:parametroEmail ")
    //los named parameters utilizan clases dto
    Optional<UserDto> getAllBirthDateAndEmail(@Param("parametroFecha") LocalDate date,
                                              @Param("parametroEmail") String email);

    List<User> findAll();
}
