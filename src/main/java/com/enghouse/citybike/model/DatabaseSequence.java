package com.enghouse.citybike.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** Database sequence.
 *
 *  @author Eimad.A
 * */
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Document(collection = "database_sequences")
public class DatabaseSequence {

    @Id
    private String id;

    private long seq;


}
