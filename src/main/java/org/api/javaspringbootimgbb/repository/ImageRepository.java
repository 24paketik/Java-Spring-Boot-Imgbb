package org.api.javaspringbootimgbb.repository;

import org.api.javaspringbootimgbb.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
