/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package commons.queue;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

/**
 * Class for the queue user.
 */
@Entity
public class QueueUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    /**
     * Constructor for the queue user.
     */
    public QueueUser() {
        // for object mapper
    }

    /**
     * Constructor for the queue user.
     * @param username the username of the player
     */
    public QueueUser(String username) {
        this.username = username;
    }

    /**
     * @return id of the QueueUser
     */
    public long getId() {
        return id;
    }

    /**
     * @return username of the QueueUser
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the id of the QueueUser as the passed long.
     *
     * @param id long value to be set as the new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * sets the username of the QueueUser as the passed string.
     *
     * @param username string value to be set as the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * checks whether the passed object is a QueueUser, and is equal (fields) to the QueueUser the method is called over.
     *
     * @param obj to be compared
     * @return boolean true/false value
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * @return readable string display of the QueueUser instance
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}