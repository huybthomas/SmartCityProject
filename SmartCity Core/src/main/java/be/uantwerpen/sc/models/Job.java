package be.uantwerpen.sc.models;

/**
 * Created by Niels on 4/05/2016.
 */
public class Job
{
    private Long jobId;
    private String jobDescription;

    public Job(String jobDescription)
    {
        this.jobId = 0L;
        this.jobDescription = jobDescription;
    }

    public Job(Long jobId, String jobDescription)
    {
        this.jobId = jobId;
        this.jobDescription = jobDescription;
    }

    public Long getJobId()
    {
        return jobId;
    }

    public void setJobId(Long jobId)
    {
        this.jobId = jobId;
    }

    public String getJobDescription()
    {
        return jobDescription;
    }

    public void setJobDescription(String jobDesciption)
    {
        this.jobDescription = jobDesciption;
    }

    @Override
    public String toString()
    {
        return "Job{" +
                "jobId=" + jobId +
                ", jobDescription='" + jobDescription + '\'' +
                '}';
    }
}
