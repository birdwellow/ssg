var gulp = require('gulp'),
    gutil = require('gulp-util'),
    clean = require('gulp-clean'),
    sass = require('gulp-sass'),
    browserSync = require('browser-sync'),
    uglify = require('gulp-uglify'),
    jshint = require('gulp-jshint'),
    rename = require('gulp-rename'),
    concat = require('gulp-concat'),
    minifyHtml = require('gulp-minify-html'),
    ngTemplate = require('gulp-ng-template');

var targetDir = 'dist/static/';
var packageJson = require('./package.json');

packageJson.gulpBuildIncludes.forEach(function (currentValue, index, array) {
  "use strict";
  array[index] = "node_modules/" + currentValue;
});

var gulpSrc = packageJson.gulpBuildIncludes.concat([
  'src/js/**/*.js',
  'src/scss/index.scss'
]);


/**
 * HTML Build
 */

gulp.task('html', function(){
  return gulp.src('src/index.html')
    .pipe(gulp.dest(targetDir))
    .pipe(browserSync.reload({stream:true, once: true}));
});

gulp.task('html:dev', function(){
  return gulp.src('src/**/*.html')
    .pipe(gulp.dest(targetDir))
    .pipe(browserSync.reload({stream:true, once: true}));
});

gulp.task('html-templates', function(){
  return gulp.src('src/js/**/*.html')
    .pipe(minifyHtml({empty: true, quotes: true}))
    .pipe(ngTemplate({
      moduleName: 'Editor',
      standalone: false,
      filePath: 'templates.js',
      prefix: 'js/'
    }))
    .pipe(gulp.dest(targetDir + 'tmp'))
    .pipe(browserSync.reload({stream:true}));
  // .pipe(browserSync.reload({stream:true, once: true}));
});


/**
 * Assets Build
 */

var gulpFontsSrc = gulpSrc.filter(function (currentValue) {
  return currentValue.indexOf('font') !== -1;
});

gulp.task('fonts',function(){
  return gulp.src(gulpFontsSrc)
    .pipe(gulp.dest(targetDir + 'fonts'))
  // .pipe(browserSync.reload({stream:true, once: true}));
});

gulp.task('assets', ['fonts']);

/**
 * JS Build
 */

var gulpJsSrc = gulpSrc.filter(function (currentValue) {
  return currentValue.indexOf('.js') !== -1;
});

gulp.task('js-sources',function(){
  return gulp.src(gulpJsSrc)
  // .pipe(jshint('.jshintrc'))
  // .pipe(jshint.reporter('default'))
    .pipe(concat('app.js'))
    .pipe(gulp.dest(targetDir + 'tmp'))
    .on('error', function (err) {
      gutil.log(gutil.colors.red('[Error]'),
        err.toString()
      );
    })
    .pipe(browserSync.reload({stream:true, once: true}));
});

gulp.task('js', ['js-sources', 'html-templates'], function(){
  gulp.src(targetDir + 'tmp/**/*.js')
    .pipe(concat('main.js'))
    .pipe(gulp.dest(targetDir + 'js'));
});

gulp.task('js:dev', ['js-sources'], function(){
  gulp.src(targetDir + 'tmp/app.js')
    .pipe(concat('main.js'))
    .pipe(gulp.dest(targetDir + 'js'));
});


/**
 * SASS Build
 */

var gulpSassSrc = gulpSrc.filter(function (currentValue) {
  return currentValue.indexOf('.scss') !== -1 || currentValue.indexOf('.css') !== -1;
});

gulp.task('css', function () {
  return gulp.src(gulpSassSrc)
    .pipe(sass().on('error', sass.logError))
    .pipe(concat('main.css'))
    .pipe(gulp.dest(targetDir + 'css'))
    .pipe(browserSync.reload({stream:true}));
});


/**
 * Clean
 */
gulp.task('clean', function() {
  gulp.src('dist/*', {read: false})
    .pipe(clean());
});


/**
 * Live reload
 */

gulp.task('browser-sync', function() {
  return browserSync.init(null, {
      server: {
          baseDir: targetDir
      }
  });
});

gulp.task('bs-reload', function () {
  return browserSync.reload();
});


/**
 * Main tasks
 */

gulp.task('watch', ['dev', 'browser-sync'], function () {
  gulp.watch("src/**/*.scss", ['css']);
  gulp.watch("src/**/*.js", ['js']);
  gulp.watch("src/**/*.html", ['html']);
  gulp.watch(targetDir + "**/*", ['bs-reload']);
});

gulp.task('prod', ['html', 'assets', 'css', 'js']);

gulp.task('dev', ['html:dev', 'assets', 'css', 'js:dev']);

gulp.task('default', ['dev']);
