package calocheck.boundedContext.friend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFriend is a Querydsl query type for Friend
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFriend extends EntityPathBase<Friend> {

    private static final long serialVersionUID = 646460622L;

    public static final QFriend friend = new QFriend("friend");

    public final calocheck.base.entity.QBaseEntity _super = new calocheck.base.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    public final NumberPath<Long> followerId = createNumber("followerId", Long.class);

    public final NumberPath<Long> followingId = createNumber("followingId", Long.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public QFriend(String variable) {
        super(Friend.class, forVariable(variable));
    }

    public QFriend(Path<? extends Friend> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFriend(PathMetadata metadata) {
        super(Friend.class, metadata);
    }

}

