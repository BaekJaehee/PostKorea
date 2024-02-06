//package com.ssafy.dmobile.specification;
//
//import com.ssafy.dmobile.entity.DetailData;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Predicate;
//
//public class DetailDataSpecifications {
//    public static Specification<DetailData> hasTag(String tagName) {
//        return (root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//            if (tagName != null) {
//                predicates.add(criteriaBuilder.like(root.get("mcodeName"), "%" + tagName + "%"));
//                predicates.add(criteriaBuilder.like(root.get("scodeName"), "%" + tagName + "%"));
//                predicates.add(criteriaBuilder.like(root.get("ccceName"), "%" + tagName + "%"));
//            }
//            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
//        };
//    }
//}
