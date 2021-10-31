package ru.job4j.grabber;

import java.util.ArrayList;
import java.util.List;

public class MemStore implements Store {

    private final List<Post> posts = new ArrayList<>();
    private int count = 1;

    @Override
    public void save(Post post) {
        posts.add(post);
        post.setId(count);
        count++;
    }

    @Override
    public List<Post> getAll() {
        return posts;
    }

    @Override
    public Post findById(int id) {
        if (id > 0 && id < count) {
            for (Post post : posts) {
                if (post.getId() == id) {
                    return post;
                }
                System.out.println("поста с указанным id : " + id + " не найдено");
            }
        } else {
            System.out.println("введен неверный id : " + id
                    + " вне значений от 1 до " + (count - 1));
        }
        return null;
    }
}
