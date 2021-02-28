package com.five.nav.service.implementation;


import com.five.nav.domain.Group;
import com.five.nav.domain.User;
import com.five.nav.exception.ArticleNotSavedException;
import com.five.nav.exception.GroupNotFoundException;
import com.five.nav.exception.GroupNotSavedException;
import com.five.nav.exception.UserNotAllowedForThisActionException;
import com.five.nav.mapper.GroupMapper;
import com.five.nav.repository.GroupRepository;
import com.five.nav.request.GroupRequest;
import com.five.nav.response.GroupResponse;
import com.five.nav.service.GroupServiceInterface;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class GroupService implements GroupServiceInterface {

  GroupRepository groupRepository;
  GroupMapper mapper;

  @Override
  public GroupResponse createGroup(GroupRequest request, User user) {
    Group group = mapper.from(request);
    group.setUser(user);

    try{
      Group savedGroup = groupRepository.save(group);
      log.info(String.format("Group with id: %d and name: %s for user: %s saved to database!",
          savedGroup.getId(), savedGroup.getName(), user.getEmail()));
      return mapper.from(savedGroup);

    }catch(Exception e){
      log.error(String.format("Group with name: %s could for user: %s not be saved. Message: %s.",
          request.getName(),user.getEmail(), e));
      throw new GroupNotSavedException(e.getMessage());
    }
  }

  @Override
  public GroupResponse updateGroup(long id, GroupRequest request, User user) {
    Optional<Group> group = groupRepository.findById(id);
    if(group.isPresent()){
      if(group.get().getUser().getId() != user.getId()){
        log.error(String.format("User with id: %d, email: %s trying to update group with id: %d "
            + "to "
            + "which "
            + "he/she is not author", user.getId(),user.getEmail(), group.get().getId()));
        throw new UserNotAllowedForThisActionException(user.getEmail());
      }else{
        if(!group.get().getName().equals(request.getName())){
          log.info("Group name changed!");
          group.get().setName(request.getName());
        }
        if(!group.get().getArticles().stream().map(x -> x.getId()).collect(Collectors.toList()).equals(request.getArticles())){
          log.info("List of articles in group changed!");

          group.get().setArticles(mapper.from(request).getArticles());
        }

        Group updatedGroup = groupRepository.save(group.get());

        return mapper.from(updatedGroup);

      }
    }else{
      log.error(String.format("Group with id: %d not found!",id));
      throw new GroupNotFoundException(id);
    }
  }

  @Override
  public List<GroupResponse> getAllGroupsForUser(User user) {

    List<Group> groups = groupRepository.findGroupsForUser(user.getId());
    log.info(String.format("Found %d groups for user: %s!", groups.size(), user.getEmail()));
    return groups.stream().map(group -> mapper.from(group)).collect(Collectors.toList());

  }

  @Override
  public GroupResponse getGroup(long id, User user) {
    Optional<Group> group = groupRepository.findGroupForUser(id, user.getId());
    if(group.isPresent()){
      log.info(String.format("Group with id: %d found.", id));
      return mapper.from(group.get());
    }else{
      log.error(String.format("Group with id: %d for user: %s not found!", id, user.getEmail()));
      throw new GroupNotFoundException(id);
    }
  }

  @Override
  public void deleteGroup(long id, User user) {
    Optional<Group> group = groupRepository.findGroupForUser(id, user.getId());
    if(group.isPresent()){
      log.info(String.format("Group with id: %id for user: %s is being deleted!", id,
          user.getEmail()));
      groupRepository.delete(group.get());
    }else{
      log.error(String.format("Group with id: %d for user: %s not found!", id, user.getEmail()));
      throw new GroupNotFoundException(id);
    }

  }
}
