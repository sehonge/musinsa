rootProject.name = "musinsa"

include(
    "api",
    "domain-jpa",
)

rootProject.children.forEach { project ->
    project.projectDir = file("subprojects/${project.name}")
    assert(project.projectDir.isDirectory)
}
