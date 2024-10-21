package com.bootcamp.demo.bc_forum2.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.bootcamp.demo.bc_forum2.dto.CommentDTO;
import com.bootcamp.demo.bc_forum2.dto.CommentSummaryDTO;
import com.bootcamp.demo.bc_forum2.dto.PostDTO;
import com.bootcamp.demo.bc_forum2.dto.UserCommentsResponseDTO;
import com.bootcamp.demo.bc_forum2.dto.UserDTO;
import com.bootcamp.demo.bc_forum2.exception.InvalidInputException;
import com.bootcamp.demo.bc_forum2.exception.RestTemplateException;
import com.bootcamp.demo.bc_forum2.exception.UserNotFoundException;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final String USERS_URL = "https://jsonplaceholder.typicode.com/users";
    private static final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";
    private static final String COMMENTS_URL = "https://jsonplaceholder.typicode.com/comments";

    private final RestTemplate restTemplate;


    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    private <T> T fetchExternalApi(String url, Class<T> responseType) {
        try {
            T response = restTemplate.getForObject(url, responseType);
            if (response == null) {
                throw new RestTemplateException("Failed to fetch data from " + url);
            }
            return response;
        } catch (RestClientException e) {
            throw new RestTemplateException("Error fetching data from " + url, e);
        }
    }

    public List<UserDTO> getAllUsersWithPostsAndComments() {
        logger.debug("Fetching all users, posts, and comments from external APIs.");


        UserDTO[] usersArray = fetchExternalApi(USERS_URL, UserDTO[].class);
        List<UserDTO> users = Arrays.asList(usersArray);


        PostDTO[] postsArray = fetchExternalApi(POSTS_URL, PostDTO[].class);
        List<PostDTO> posts = Arrays.asList(postsArray);

     
        CommentDTO[] commentsArray = fetchExternalApi(COMMENTS_URL, CommentDTO[].class);
        List<CommentDTO> comments = Arrays.asList(commentsArray);

    
        Map<Integer, List<CommentDTO>> commentsByPostId = comments.stream()
                .collect(Collectors.groupingBy(CommentDTO::getPostId));


        for (PostDTO post : posts) {
            List<CommentDTO> postComments = commentsByPostId.getOrDefault(post.getId(), new ArrayList<>());
            post.setComments(postComments);
        }

 
        Map<Integer, List<PostDTO>> postsByUserId = posts.stream()
                .collect(Collectors.groupingBy(PostDTO::getUserId));

    
        for (UserDTO user : users) {
            List<PostDTO> userPosts = postsByUserId.getOrDefault(user.getId(), new ArrayList<>());
            user.setPosts(userPosts);
        }

        logger.debug("Successfully fetched and organized users, posts, and comments.");
        return users;
    }


    public UserDTO getUserById(int userId) {
        if (userId <= 0) {
            throw new InvalidInputException();
        }

        List<UserDTO> users = getAllUsersWithPostsAndComments();

        Optional<UserDTO> userOptional = users.stream()
                .filter(user -> user.getId() == userId)
                .findFirst();

        if (!userOptional.isPresent()) {
            throw new UserNotFoundException();
        }

        return userOptional.get();
    }


    public int getTotalUsers() {
        UserDTO[] usersArray = fetchExternalApi(USERS_URL, UserDTO[].class);
        return usersArray.length;
    }

    public int getTotalPosts() {
        PostDTO[] postsArray = fetchExternalApi(POSTS_URL, PostDTO[].class);
        return postsArray.length;
    }


    public UserCommentsResponseDTO getCommentsByUserId(int userId) {
        if (userId <= 0) {
            throw new InvalidInputException();
        }

        try {
      
            UserDTO[] usersArray = fetchExternalApi(USERS_URL, UserDTO[].class);
            List<UserDTO> users = Arrays.asList(usersArray);

         
            Optional<UserDTO> userOptional = users.stream()
                    .filter(user -> user.getId() == userId)
                    .findFirst();

            if (!userOptional.isPresent()) {
                throw new UserNotFoundException();
            }

            UserDTO user = userOptional.get();

    
            PostDTO[] postsArray = fetchExternalApi(POSTS_URL, PostDTO[].class);
            List<PostDTO> posts = Arrays.asList(postsArray);


            List<PostDTO> userPosts = posts.stream()
                    .filter(post -> post.getUserId() == userId)
                    .collect(Collectors.toList());

          
            CommentDTO[] commentsArray = fetchExternalApi(COMMENTS_URL, CommentDTO[].class);
            List<CommentDTO> comments = Arrays.asList(commentsArray);

            List<CommentSummaryDTO> userCommentsSummary = new ArrayList<>();

            if (!userPosts.isEmpty()) {
          
                Set<Integer> userPostIds = userPosts.stream()
                        .map(PostDTO::getId)
                        .collect(Collectors.toSet());

                userCommentsSummary = comments.stream()
                        .filter(comment -> userPostIds.contains(comment.getPostId()))
                        .map(comment -> {
                            CommentSummaryDTO summary = new CommentSummaryDTO();
                            summary.setName(comment.getName());
                            summary.setEmail(comment.getEmail());
                            summary.setBody(comment.getBody());
                            return summary;
                        })
                        .collect(Collectors.toList());
            }

      
            UserCommentsResponseDTO response = new UserCommentsResponseDTO();
            response.setId(user.getId());
            response.setUsername(user.getUsername());
            response.setComments(userCommentsSummary);

            return response;

        } catch (RestClientException e) {
            throw new RestTemplateException(e);
        }
    }
}