package com.pretest.solution.solution01_01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Graph 구조로 구현
 * -> 일반적인 Tree 구조만으로는 다중 소속 관계와 계층 구조를 모두 만족하는 설계 구현에는 제약이 있음
 */

class Category {
    int id;
    String name;
    List<Category> children;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void addChild(Category child) {
        children.add(child);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<Category> getChild() {
        return children;
    }

    public String toJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}

class CategoryNode {
    private Map<Integer, Category> categoryMap;

    public CategoryNode() {
        categoryMap = new HashMap<>();
    }

    public void addCategory(int categoryId, String categoryName) {
        categoryMap.put(categoryId, new Category(categoryId, categoryName));
    }

    public void addRelationship(int parentCategoryId, int childCategoryId) {
        Category parent = categoryMap.get(parentCategoryId);
        Category child = categoryMap.get(childCategoryId);


        if (parent != null && child != null && isChildPresent(parent, child)) {
            parent.addChild(child);
        }
    }

    private boolean isChildPresent(Category parent, Category child) {
        for (Category cx : parent.children) {
            if (cx.id == child.id) {
                return false;
            }
        }
        return true;
    }

    public Category getCategoryById(int id) {
        return categoryMap.get(id);
    }

    public Category getCategoryByName(String name) {
        for (Category category : categoryMap.values()) {
            if (category.name.equals(name)) {
                return category;
            }
        }
        return null;
    }
}

public class Solution01_01 {
    public static void main(String[] args) throws JsonProcessingException {
        CategoryNode categoryTree = new CategoryNode();

        //카테고리 생성
        categoryTree.addCategory(1, "남자");
        categoryTree.addCategory(2, "여자");
        categoryTree.addCategory(3, "엑소");
        categoryTree.addCategory(4, "방탄소년단");
        categoryTree.addCategory(5, "블랙핑크");
        categoryTree.addCategory(6, "공지사항");
        categoryTree.addCategory(7, "백현");
        categoryTree.addCategory(8, "시우민");
        categoryTree.addCategory(9, "익명게시판");
        categoryTree.addCategory(10, "공지사항");
        categoryTree.addCategory(11, "뷔");
        categoryTree.addCategory(12, "공지사항");
        categoryTree.addCategory(13, "로제");

        //부모,자식 관계 추가
        categoryTree.addRelationship(1, 3);
        categoryTree.addRelationship(1, 4);
        categoryTree.addRelationship(2, 5);
        categoryTree.addRelationship(3, 9);
        categoryTree.addRelationship(3, 7);
        categoryTree.addRelationship(3, 8);
        categoryTree.addRelationship(4, 10);
        categoryTree.addRelationship(4, 9);
        categoryTree.addRelationship(4, 11);
        categoryTree.addRelationship(5, 12);
        categoryTree.addRelationship(5, 12);
        categoryTree.addRelationship(5, 13);

        //카테고리 조회
        Category categoryById = categoryTree.getCategoryById(1);
        Category categoryByName = categoryTree.getCategoryByName("여자");

        String json = toJson(categoryById);
        System.out.println(json);
    }

    private static String toJson(Category category) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        if (category != null) {
            return objectMapper.writeValueAsString(category);
        } else {
            return "검색 결과가 없습니다.";
        }
    }
}