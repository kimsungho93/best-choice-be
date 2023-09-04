package com.winnow.bestchoice.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.winnow.bestchoice.model.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.winnow.bestchoice.entity.QChoice.choice;
import static com.winnow.bestchoice.entity.QComment.comment;
import static com.winnow.bestchoice.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<CommentDto> getPageByPostId(Pageable pageable, OrderSpecifier<?> type, long postId) {//TODO select query 추가
        List<CommentDto> content = jpaQueryFactory.select(Projections.bean(CommentDto.class,
                        comment.id, member.id.as("memberId"), member.nickname, choice.choices,
                        comment.content, comment.likeCount, comment.createdDate, comment.deletedDate))
                .from(comment).where(comment.post.id.eq(postId))
                .join(comment.member, member)
                .leftJoin(choice).on(comment.member.id.eq(choice.member.id), choice.post.id.eq(postId))
                .orderBy(type)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, 10);
    }
}
