package WebGitRepository;

public class WebGitRepositoryFactory {
    public static WebGitRepository create(String remote, String revision, String path, String line) {

        remote = remote.replace(".git", "");
        remote = remote.replaceAll("//.*@", "/");
        if(remote.matches("^.*bitbucket.*$")) {
            return new BitBucketRepository(remote, revision, path, line);
        }
        return new WebGitRepository(remote, revision, path, line);
    }
}
