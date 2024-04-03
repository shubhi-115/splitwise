package com.example.Splitwise.controller;

import com.example.Splitwise.dto.*;
import com.example.Splitwise.exception.GroupNotFoundException;
import com.example.Splitwise.model.Currency;
import com.example.Splitwise.repository.GroupRepository;
import com.example.Splitwise.repository.UserRepository;
import com.example.Splitwise.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Splitwise.model.User;
import com.example.Splitwise.model.Group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;
    @GetMapping("/settleUp/{groupId}")
    public ResponseEntity settleUpForGroup(@PathVariable("groupId") int groupId) throws GroupNotFoundException{
        List<TransactionDTO> transactions = groupService.settleUpByGroupId(groupId);
        return ResponseEntity.ok(transactions);
    }
    @PostMapping("/user")
    public ResponseEntity createUser(@RequestBody UserDTO user){
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setCreatedAt(new Date());
        newUser.setLastModifiedAt(new Date());
        User created_user = userRepository.save(newUser);
        return ResponseEntity.ok(created_user);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getUser(@PathVariable Integer userId){
        User user = this.userRepository.findById(userId).get();
        ResponseUserDTO responseUser = new ResponseUserDTO();
        responseUser.setName(user.getName());
        responseUser.setEmail(user.getEmail());
        responseUser.setPhoneNumber(user.getPhoneNumber());
        return ResponseEntity.ok(responseUser);
    }

    private List<ResponseUserDTO> setUserDTO(List<User> users){
        List<ResponseUserDTO> responseUsers = new ArrayList<>();
        for(User user: users) {
            ResponseUserDTO resUser = new ResponseUserDTO();
            resUser.setName(user.getName());
            resUser.setEmail(user.getEmail());
            resUser.setPhoneNumber(user.getPhoneNumber());
            responseUsers.add(resUser);
        }
        return responseUsers;
    }
    private List<ResponseGroupDTO> setGroupDTO(List<Group> groups){
        List<ResponseGroupDTO> resGroups = new ArrayList<>();
        for(Group group: groups){
            ResponseGroupDTO responseGroup = new ResponseGroupDTO();
            responseGroup.setGroupId(group.getId());
            responseGroup.setName(group.getName());
            responseGroup.setDescription(group.getDescription());
            responseGroup.setTotalAmountSpent(group.getTotalAmountSpent());
            List<ResponseUserDTO> responseGroupUsers = setUserDTO(group.getUsers());
            responseGroup.setUsers(responseGroupUsers);
            resGroups.add(responseGroup);
            }
        return resGroups;
    }
    @GetMapping("/user")
    public ResponseEntity getAllUsers(){
        List<User> users = this.userRepository.findAll();
        List<ResponseUserDTO> resUsers = setUserDTO(users);
        return ResponseEntity.ok(resUsers);
    }
    @GetMapping("/group/{groupId}")
    public ResponseEntity getGroup(@PathVariable int groupId){
        Group group = this.groupRepository.findById(groupId).get();
        ResponseGroupDTO responseGroup = new ResponseGroupDTO();
        responseGroup.setGroupId(group.getId());
        responseGroup.setName(group.getName());
        responseGroup.setDescription(group.getDescription());
        responseGroup.setTotalAmountSpent(group.getTotalAmountSpent());
        List<User> groupUsers = group.getUsers();
        List<ResponseUserDTO> responseUsers = setUserDTO(groupUsers);
        responseGroup.setUsers(responseUsers);
        return ResponseEntity.ok(responseGroup);
    }

    @GetMapping("/group")
    public ResponseEntity getAllGroups(){
        List<Group> groups = this.groupRepository.findAll();
        List<ResponseGroupDTO> resGroups = setGroupDTO(groups);
        return ResponseEntity.ok(resGroups);
    }
    @PostMapping("/group")
    public ResponseEntity createGroup(@RequestBody GroupDTO group){
        Group newGroup = new Group();
        newGroup.setName(group.getName());
        newGroup.setDescription(group.getDescription());
        newGroup.setTotalAmountSpent(group.getTotalAmountSpent());
        newGroup.setDefaultCurrency(Currency.INR);
        List<User> assignUsers = new ArrayList<>();
        List<Integer> userIds = group.getUserId();
        for(Integer userId: userIds){
            User user = this.userRepository.findById(userId).get();
            assignUsers.add(user);
        }
        newGroup.setUsers(assignUsers);
        groupRepository.save(newGroup);
        return ResponseEntity.ok(newGroup.getId());
    }

    @PatchMapping("/group/{groupId}/user")
    public ResponseEntity assignGroupUsers(
            @PathVariable int groupId,
            @RequestBody RequestGroupUserDTO userIds){
        Group group = this.groupRepository.findById(groupId).get();
        List<User> newGroupUsers = this.userRepository.findAllById(userIds.getUserId());
        if (group.getUsers() != null){
            List<User> exitedGroupUsers = group.getUsers();
            newGroupUsers.addAll(exitedGroupUsers);
        }
        group.setUsers(newGroupUsers);
        groupRepository.save(group);
        List<Group> groups = Collections.singletonList(group);
        List<ResponseGroupDTO> resGroup = setGroupDTO(groups);
        return ResponseEntity.ok(resGroup);
    }

    @PostMapping("/expense/{expenseId}/user")
    public ResponseEntity setUserExpense(@PathVariable int expenseId, @RequestBody RequestUserExpenseDTO userExpense){
        return ResponseEntity.ok("Entered");
    }
}
