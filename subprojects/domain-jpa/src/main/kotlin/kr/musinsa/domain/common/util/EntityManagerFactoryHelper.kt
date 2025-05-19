package kr.musinsa.domain.common.util

import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter

object EntityManagerFactoryHelper {
    fun createJpaVendorAdapter(
        jpaProperties: JpaProperties,
    ): JpaVendorAdapter {
        return HibernateJpaVendorAdapter().apply {
            setShowSql(jpaProperties.isShowSql)
            setGenerateDdl(jpaProperties.isGenerateDdl)
            if (jpaProperties.database != null) {
                setDatabase(jpaProperties.database)
            }
            if (jpaProperties.databasePlatform != null) {
                setDatabasePlatform(jpaProperties.databasePlatform)
            }
        }
    }

    fun createJpaPropertyMap(
        hibernateProperties: HibernateProperties,
        jpaProperties: JpaProperties,
    ): MutableMap<String, Any> {
        val settings = HibernateSettings().ddlAuto { hibernateProperties.ddlAuto }
        return hibernateProperties.determineHibernateProperties(jpaProperties.properties, settings)
    }
}
