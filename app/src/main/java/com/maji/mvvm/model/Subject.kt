package com.maji.mvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "subject")
class Subject() {

    @PrimaryKey(autoGenerate = true)
    var id: Int =0
            
    @ColumnInfo(name = "current_user_url")
    lateinit var current_user_url:   String

    @ColumnInfo(name = "auth_html_url")
    var current_user_authorizations_html_url:   String? = null

    @ColumnInfo(name = "auth_url")
     var authorizations_url:   String? = null

    @ColumnInfo(name = "search_url")
     var code_search_url:   String? = null

    @ColumnInfo(name = "create_time")
     var create_time:   String? = null

    @ColumnInfo(name = "commit_search_url")
     var commit_search_url:   String? = null

    @Ignore
     var current_user_repositories_url:   String? = null

    @Ignore
     var emails_url:   String? = null

    @Ignore
     var emojis_url:   String? = null

    @Ignore
     var events_url:   String? = null

    @Ignore
     var feeds_url:   String? = null

    @Ignore
     var followers_url:   String? = null

    @Ignore
     var following_url:   String? = null

    @Ignore
     var gists_url:   String? = null

    @Ignore
     var hub_url:   String? = null

    @Ignore
     var issue_search_url:   String? = null

    @Ignore
     var issues_url:   String? = null

    @Ignore
     var keys_url:   String? = null

    @Ignore
     var label_search_url:   String? = null

    @Ignore
     var notifications_url:   String? = null

    @Ignore
     var organization_repositories_url:   String? = null

    @Ignore
     var organization_teams_url:   String? = null

    @Ignore
     var organization_url:   String? = null

    @Ignore
     var public_gists_url:   String? = null

    @Ignore
     var rate_limit_url:   String? = null

    @Ignore
     var repository_search_url:   String? = null

    @Ignore
     var repository_url:   String? = null

    @Ignore
     var starred_gists_url:   String? = null

    @Ignore
     var starred_url:   String? = null

    @Ignore
     var user_organizations_url:   String? = null

    @Ignore
     var user_repositories_url:   String? = null

    @Ignore
     var user_search_url:   String? = null

    @Ignore
     var user_url:   String? = null
}