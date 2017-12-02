package WebGitRepository;

public class WebGitRepository {
    String remote;
    String path;
    String revision;
    String line;
    WebGitRepository(String remote, String revision, String path, String line) {
        this.remote = remote;
        this.path = path;
        this.revision = revision;
        this.line = line;
    }

    public String url() {
        return this.remote + "/blob/" + this.revision + this.path +"#L"+ this.line;
    }
}
