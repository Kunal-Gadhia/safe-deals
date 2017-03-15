package com.vsquaresystem.safedeals.video;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class VideoDAL {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String TABLE_NAME = "video";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertVideo;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String VIDEO_URL = "video_url";
        public static final String IS_INTRO_VIDEO = "is_intro_video";
        public static final String PROJECT_ID = "project_id";
        public static final String PROPERTY_ID = "property_id";
    };

    @Autowired
    public VideoDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertVideo = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.DESCRIPTION,
                        Columns.VIDEO_URL,
                        Columns.IS_INTRO_VIDEO,
                        Columns.PROJECT_ID,
                        Columns.PROPERTY_ID)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Video> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Video.class));
    }

    public List<Video> findAllVideos() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(Video.class));
    }

    public Video findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Video.class));
    }

    public Video insert(Video video) throws JsonProcessingException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, video.getName());
        parameters.put(Columns.DESCRIPTION, video.getDescription());
        parameters.put(Columns.VIDEO_URL, video.getVideoUrl());
        parameters.put(Columns.IS_INTRO_VIDEO, video.getIsIntroVideo());
        parameters.put(Columns.PROJECT_ID, video.getProjectId());
        parameters.put(Columns.PROPERTY_ID, video.getPropertyId());
        Number newId = insertVideo.executeAndReturnKey(parameters);
        video = findById(newId.intValue());
        return video;
    }

    public Video findIntroVideo() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.IS_INTRO_VIDEO + " = true";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(Video.class));
    }

    public Video update(Video video) throws JsonProcessingException {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ? ,"
                + Columns.DESCRIPTION + " = ? , "
                + Columns.VIDEO_URL + " = ?  , "
                + Columns.IS_INTRO_VIDEO + " = ?  , "
                + Columns.PROJECT_ID + " = ?  , "
                + Columns.PROPERTY_ID + " = ?  WHERE "
                + Columns.ID + " = ?";
        ObjectMapper mapper = new ObjectMapper();
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            video.getName(),
            video.getDescription(),
            video.getVideoUrl(),
            video.getIsIntroVideo(),
            video.getProjectId(),
            video.getPropertyId(),
            video.getId()});
        video = findById(video.getId());
        return video;
    }

    private final RowMapper<Video> videoRowMapper = new RowMapper<Video>() {
        @Override
        public Video mapRow(ResultSet rs, int i) throws SQLException {
            Video video = new Video();
            video.setId(rs.getInt(Columns.ID));
            video.setName(rs.getString(Columns.NAME));
            video.setDescription(rs.getString(Columns.DESCRIPTION));
            video.setVideoUrl(rs.getString(Columns.VIDEO_URL));
            video.setIsIntroVideo(rs.getBoolean(Columns.IS_INTRO_VIDEO));
            video.setProjectId(rs.getInt(Columns.PROJECT_ID));
            video.setPropertyId(rs.getInt(Columns.PROPERTY_ID));
            return video;
        }
    };

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

}
