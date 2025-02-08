package com.yuhao.acp.repository;

import com.yuhao.acp.model.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepo extends JpaRepository<Key, String> {

}
