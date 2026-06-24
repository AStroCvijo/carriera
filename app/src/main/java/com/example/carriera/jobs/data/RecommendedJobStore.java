package com.example.carriera.jobs.data;

import com.example.carriera.jobs.model.RecommendedJob;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecommendedJobStore {
    private final List<RecommendedJob> jobs = new ArrayList<>();

    public static RecommendedJobStore createDemoStore() {
        RecommendedJobStore store = new RecommendedJobStore();
        store.jobs.add(new RecommendedJob(
                "levi9-junior",
                "Junior Software Developer",
                "Levi9",
                "Novi Sad",
                "Remote",
                "Full-time",
                "May 15",
                "May 31",
                92,
                "We are looking for a junior developer to join our backend team. You will work on real projects and learn from experienced engineers while building modern web applications.",
                new String[]{"Java", "Spring Boot", "SQL", "Git", "REST API", "Docker"},
                new String[]{"Java", "REST API", "SQL", "Problem solving", "Git"},
                new String[]{"Docker", "Spring Security"}
        ));
        store.jobs.add(new RecommendedJob(
                "vegait-backend",
                "Backend Developer Intern",
                "Vega IT",
                "Novi Sad",
                "Hybrid",
                "Internship",
                "May 21",
                "June 05",
                84,
                "Join a backend internship team and help build APIs, database integrations and production-ready services with guidance from senior mentors.",
                new String[]{"Java", "REST API", "SQL", "Git", "Docker"},
                new String[]{"Java", "REST API", "Git", "Problem solving"},
                new String[]{"Docker", "SQL"}
        ));
        store.jobs.add(new RecommendedJob(
                "schneider-java",
                "Java Developer Intern",
                "Schneider Electric",
                "Novi Sad",
                "On-site",
                "Internship",
                "May 20",
                "June 01",
                72,
                "Work with an engineering team on Java services, internal tools and clean technical documentation for industrial software products.",
                new String[]{"Java", "SQL", "Git", "Testing"},
                new String[]{"Java", "Git", "Problem solving"},
                new String[]{"SQL", "Testing"}
        ));
        return store;
    }

    public List<RecommendedJob> all() {
        return Collections.unmodifiableList(jobs);
    }

    public List<RecommendedJob> filtered(String filter) {
        List<RecommendedJob> result = new ArrayList<>();
        for (RecommendedJob job : jobs) {
            if ("High match".equals(filter) && job.matchPercent < 85) {
                continue;
            }
            if ("Remote".equals(filter) && !"Remote".equals(job.workMode)) {
                continue;
            }
            result.add(job);
        }
        if ("Newest".equals(filter)) {
            Collections.reverse(result);
        }
        return result;
    }

    public RecommendedJob require(String id) {
        for (RecommendedJob job : jobs) {
            if (job.id.equals(id)) {
                return job;
            }
        }
        throw new IllegalArgumentException("Unknown job id: " + id);
    }
}
