package kr.musinsa.domain.common.config

import jakarta.persistence.EntityManagerFactory
import kr.musinsa.domain.common.util.EntityManagerFactoryHelper
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.hibernate5.SpringBeanContainer
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager",
    basePackages = ["kr.musinsa.domain"],
)
class JpaConfig {
    @Primary
    @Bean("entityManagerFactory")
    @DependsOn("dataSource")
    fun entityManagerFactory(
        @Qualifier("dataSource") dataSource: DataSource,
        jpaProperties: JpaProperties,
        hibernateProperties: HibernateProperties,
        beanFactory: ConfigurableListableBeanFactory,
    ): LocalContainerEntityManagerFactoryBean {
        val entityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        entityManagerFactoryBean.dataSource = dataSource
        entityManagerFactoryBean.setPackagesToScan("kr.musinsa.domain")
        entityManagerFactoryBean.persistenceUnitName = "entityManager"

        entityManagerFactoryBean.jpaVendorAdapter = EntityManagerFactoryHelper.createJpaVendorAdapter(jpaProperties)
        entityManagerFactoryBean.setJpaPropertyMap(EntityManagerFactoryHelper.createJpaPropertyMap(hibernateProperties, jpaProperties))
        entityManagerFactoryBean.jpaPropertyMap[AvailableSettings.BEAN_CONTAINER] = SpringBeanContainer(beanFactory)

        return entityManagerFactoryBean
    }

    @Primary
    @Bean("transactionManager")
    fun transactionManager(
        @Qualifier("entityManagerFactory") entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager = JpaTransactionManager(entityManagerFactory)
}
