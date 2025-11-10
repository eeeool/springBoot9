package com.example.demo9.service;

import com.example.demo9.entity.Guest;
import com.example.demo9.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestService {

  private final GuestRepository guestRepository;

  public Page<Guest> getGuestList(int pag, int pageSize) {
    PageRequest pageable = PageRequest.of(pag, pageSize, Sort.by(Sort.Order.desc("id")));
    return guestRepository.findAll(pageable);
  }

  public void setGuestInput(Guest guest) {
    guestRepository.save(guest);
  }

  public void setGuestDelete(Long id) {
    guestRepository.deleteById(id);
  }
}
