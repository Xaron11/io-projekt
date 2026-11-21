{
  description = "JSON Tools - IO project";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";

  outputs =
    { nixpkgs, ... }:
    let
      systems = [
        "x86_64-linux"
        "aarch64-linux"
        "aarch64-darwin"
      ];
      forAllSystems = f: nixpkgs.lib.genAttrs systems (system: f nixpkgs.legacyPackages.${system});
    in
    {
      devShells = forAllSystems (
        pkgs:
        let
          jdk = pkgs.jdk11;
        in
        {
          default = pkgs.mkShell {
            packages = [
              jdk
              (pkgs.maven.override { jdk_headless = jdk; })
              pkgs.httpie
              pkgs.curl
              pkgs.jq
              pkgs.jdt-language-server
              pkgs.pre-commit
            ];

            JAVA_HOME = jdk.home;
          };
        }
      );

      formatter = forAllSystems (pkgs: pkgs.nixfmt-tree);
    };
}
